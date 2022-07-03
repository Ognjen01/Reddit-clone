export interface Comment {
    text: string;
    timestamp: Date;
    isDeleted: boolean;
    userId: number;
    postId: number | null;
    commentId: number | null;
    repliesToCommentId: number | null
    //reactions: [key:number, value: Reaction];
}