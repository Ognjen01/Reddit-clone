export interface ResetPassword {
    userId: number;
    oldPassword: string;
    newPassword: string;
    confirmNewPassword: string;
}