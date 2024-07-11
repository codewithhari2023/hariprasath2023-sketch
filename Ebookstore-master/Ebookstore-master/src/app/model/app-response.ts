export interface AppResponse {
    message(message: any): unknown;
    status: number;
    timestamp: String;
    data: any;
    error: any;
}
