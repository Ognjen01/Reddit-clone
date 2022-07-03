export interface Post {
    id: number;
    title: string;
    text: string;
    creationDate: Date;
    imagePath: string | null;
    userId: number;
    flairId: number | null;
    communityId: number;
    // reaction: [key:number, value: Reaction];
    // flair: Falir;
    // community: Community;
    // user: User;
    // comment: [key:number, value: Comment];
}