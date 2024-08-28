import { Button } from "@/components/ui/button"

interface RefreshButtonProps {
  handleRefresh: () => void
}
export default function RefreshButton({ handleRefresh }: RefreshButtonProps) {
  return (
    <Button
      className={"bg-blue-500 hover:bg-blue-400 h-[30px]"}
      onClick={handleRefresh}
    >
      새로고침
    </Button>
  )
}
