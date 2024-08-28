import { Button } from "@/components/ui/button"

interface EditButtonProps {
  setEditMode: (editMode: boolean) => void
}

export default function EditButton({ setEditMode }: EditButtonProps) {
  return (
    <Button
      className={"bg-emerald-500 hover:bg-emerald-400  h-[30px]"}
      onClick={() => {
        setEditMode(true)
      }}
    >
      편집하기
    </Button>
  )
}
