export default function checkLogin() {
  let userData = localStorage.getItem("userData")

  if (userData === null) {
    fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/users/me`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (res.status === 401) {
          return
        }
        localStorage.setItem("userData", JSON.stringify(res))
      })
      .catch((err) => {
        console.error(err)
      })
  }
}
