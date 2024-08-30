package com.talkka.server.admin.scheduler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;

import com.talkka.server.admin.dto.SchedulerReqDto;
import com.talkka.server.admin.dto.SchedulerRespDto;
import com.talkka.server.admin.exception.SchedulerNotFoundException;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@EnableScheduling
@Component
@RequiredArgsConstructor
// 나중에 Quartz Scheduler 로 리팩토링
public class DynamicSchedulingConfig {

	private ScheduledTaskRegistrar registrar;
	private final ApplicationContext applicationContext;
	private final Map<String, Runnable> taskMap = new HashMap<>();
	private final MultiValueMap<String, ScheduledFuture<?>> scheduledTasks = new LinkedMultiValueMap<>();
	private final MultiValueMap<String, String> cronMap = new LinkedMultiValueMap<>();

	@PostConstruct
	public void init() {
		registrar = new ScheduledTaskRegistrar();
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(20);
		taskScheduler.initialize();
		registrar.setTaskScheduler(taskScheduler);

		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			if (beanName.equals("dynamicSchedulingConfig")) {
				continue;
			}
			Object bean = applicationContext.getBean(beanName);
			Method[] methods = bean.getClass().getDeclaredMethods();

			for (Method method : methods) {
				DynamicScheduled annotation = method.getAnnotation(DynamicScheduled.class);
				if (annotation != null) {
					String[] crons = annotation.cron().split("\\|");
					String name = annotation.name();
					Runnable task = () -> ReflectionUtils.invokeMethod(method, bean);
					taskMap.put(name, task);

					for (String cron : crons) {
						cron.trim();
						ScheduledFuture<?> future = registrar.getScheduler()
							.schedule(task, triggerContext -> new CronTrigger(cron).nextExecution(triggerContext));
						scheduledTasks.add(name, future);
						cronMap.add(name, cron);
					}
				}
			}
		}
	}

	public List<SchedulerRespDto> getSchedulers() {
		List<SchedulerRespDto> schedulers = new ArrayList<>();
		for (String name : taskMap.keySet()) {
			schedulers.add(new SchedulerRespDto(name, cronMap.get(name)));
		}
		return schedulers;
	}

	public void updateCronExpression(SchedulerReqDto dto) throws SchedulerNotFoundException {
		// 현재 해당 메소드의 스케줄링 작업 전부 멈추고 맵에서 삭제
		scheduledTasks.get(dto.name()).forEach(future -> future.cancel(false));
		scheduledTasks.remove(dto.name());
		// 해당 메소드 가져옴
		Runnable task = taskMap.get(dto.name());
		// 크론식마다 스케줄 작업 추가
		for (String cron : dto.cronString().split("\\|")) {
			cron.trim();
			ScheduledFuture<?> future = registrar.getScheduler()
				.schedule(task, triggerContext -> new CronTrigger(cron).nextExecution(triggerContext));
			scheduledTasks.add(dto.name(), future);
			cronMap.add(dto.name(), cron);
		}
	}
}
