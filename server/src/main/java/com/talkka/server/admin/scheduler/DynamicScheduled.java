package com.talkka.server.admin.scheduler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DynamicScheduled {
	String cron() default "0 0/1 * * * *";  // 기본 cron 표현식

	String name();  // 작업 이름
}
