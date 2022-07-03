import { Rule } from 'src/app/model/rule';

export interface Community {
    communityId: number;
    name: string;
    description: string;
    creationDate: Date;
    isSuspended: boolean;
    suspendedReason: string;
    rules : Rule[] | null;
    moderatorId: number, 
    
    // moderators: [key:number, value: User];
    // flairs: [key:number, value: Falir];
    // rules: [key:number, value: Rule];
    // posts: [key:number, value: Post];
}