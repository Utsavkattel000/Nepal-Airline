function validatePhoneNumber() {
    // Get the input element
    var phoneNumberInput = document.getElementsByName("phone")[0];

    // Get the value of the input
    var phoneNumber = phoneNumberInput.value;

    // Regular expression to match the criteria: starts with 97 or 98, and is 10 digits long
    var regex = /^(97|98)\d{8}$/;

    // Check if the input value matches the regular expression
    if (regex.test(phoneNumber)) {
        // Valid input, remove any previous error message
        phoneNumberInput.setCustomValidity('');
    } else {
        // Invalid input, set custom error message
        phoneNumberInput.setCustomValidity('Phone number must be 10 digits long and start with 97 or 98.');
    }
}

// Attach the function to the input's "input" event to trigger validation on input change
document.getElementsByName("phone")[0].addEventListener('input', validatePhoneNumber);
function validatePassword() {
    // Get the input elements
    var passwordInput = document.getElementsByName("password")[0];
    var password2Input = document.getElementsByName("password2")[0];

    // Get the values of the inputs
    var password = passwordInput.value;
    var password2 = password2Input.value;

    // Regular expressions for password criteria
    var lengthRegex = /^.{8,15}$/;

    // Check if password meets length requirements
    if (!lengthRegex.test(password)) {
        passwordInput.setCustomValidity('Password must be between 8 and 15 characters long.');
    } else {
        passwordInput.setCustomValidity('');
    }

    if (!lengthRegex.test(password2)) {
        password2Input.setCustomValidity('Password must be between 8 and 15 characters long.');
    } else {
        password2Input.setCustomValidity('');
    }
}

document.getElementsByName("password")[0].addEventListener('input', validatePassword);
document.getElementsByName("password2")[0].addEventListener('input', validatePassword);
var fullname = document.getElementById('fullName');
fullname.addEventListener('input', function () {
        var pattern = /^[a-zA-Z\s]*$/;
        if (!pattern.test(this.value)) {
            this.setCustomValidity('Full name should only contain a to z, A to Z and spaces.');
        } else {
            this.setCustomValidity('');
        }
    });


