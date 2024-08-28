export type TransportType = "BUS" | "SUBWAY"

export function getTransportTypeFromString(
  transportType: string
): TransportType {
  const validTransportTypes: TransportType[] = ["BUS", "SUBWAY"]
  if (!validTransportTypes.includes(transportType as TransportType)) {
    throw new InvalidTransportTypeError(transportType)
  }
  return transportType as TransportType
}

export class InvalidTransportTypeError extends Error {
  constructor(transportType: string) {
    super(`Invalid transport type: ${transportType}`)
  }
}
