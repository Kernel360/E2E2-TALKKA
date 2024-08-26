export default class Rating {
  constructor(public rating: number) {
    if (rating < 0 || rating > 10) {
      throw new InvalidRatingError(rating)
    }
    this.rating = rating
  }

  //  rating 은 0~10 사이의 정수 값이다. 따라서 2로 나누어서 0~5 사이의 값을 리턴한다.
  getRatingViewValue(): number {
    return this.rating / 2
  }
}

export class InvalidRatingError extends Error {
  constructor(rating: number) {
    super(`Invalid rating: ${rating}`)
  }
}
