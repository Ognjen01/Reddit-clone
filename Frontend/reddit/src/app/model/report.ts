export interface Report {
    reportId: number;
    reason: string;
    timestamp: Date;
    accepted: boolean;
    userId: number;
    postId: number | null;
    commentId: number | null;
}