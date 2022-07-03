export interface Reaction {
    reactionId: number;
    reactionType: string;
    timestamp: Date; 
    commentId: Comment;
    postId: number;
    userId: number;
}