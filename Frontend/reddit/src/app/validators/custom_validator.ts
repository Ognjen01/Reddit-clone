import { AbstractControl, ValidatorFn, ValidationErrors } from '@angular/forms';

export function validateTestFactory(): ValidatorFn {
    return (c: AbstractControl) => {

        const isValid = c.value === 'Stefan';

        if (isValid) {
            return null;
        } else {
            return {
                testError: {
                    valid: false
                }
            };
        }
    };

}

export function validateEmail(): ValidatorFn {
    return (c: AbstractControl) => {

        const isValid = c.value;

        if (isValid) {
            return null;
        } else {
            return {
                testError: {
                    valid: false
                }
            };
        }
    };

}

export function validatePassword(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
        if (control.value == '') return null;
  
        let re = new RegExp('^[a-zA-Z ]*$');
        if (re.test(control.value)) {
          return null;
        } else {
          return { password: true };
        }
      };

}