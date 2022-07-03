export interface User {
    userId: number;
    username: string;
    registrationDate: Date;
    description: string;
    displayName: string;
    email: string;
    avatar: string;
    password: string;
    confirmPassword: string;
    userType: string;
    // communities: [key:number, value: Community];
    // posts: [key:number, value: Post];
    // comments: [key:number, value: Comment];
    // reactions: [key:number, value: Reaction];
}