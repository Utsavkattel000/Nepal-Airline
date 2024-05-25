
// Function to increment the number of forms
function increment() {
    var numberOfForms = document.getElementById('numberOfForms');
    if (numberOfForms.value < 4) {
        numberOfForms.value++;
    }
}

// Function to decrement the number of forms
function decrement() {
    var numberOfForms = document.getElementById('numberOfForms');
    if (numberOfForms.value > 1) {
        numberOfForms.value--;
    }
}
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
var ageInput = document.getElementById('ageInput');

  // Add event listener for input
  ageInput.addEventListener('input', function() {
    // Get the entered age value
    var age = parseInt(ageInput.value);

    // Check if the age is within the range of 1 to 120
    if (age < 1 || age > 120) {
      // If not, set the value to empty
      ageInput.value = '';
    }
  });
  var fullname = document.getElementById('fullName');
fullname.addEventListener('input', function () {
        var pattern = /^[a-zA-Z\s]*$/;
        if (!pattern.test(this.value)) {
            this.setCustomValidity('Full name should only contain a to z, A to Z and spaces.');
        } else {
            this.setCustomValidity('');
        }
    });

  var nationality = document.getElementById('nationality');
      nationality.addEventListener('input', function () {
        var pattern = /^[a-zA-Z\s]*$/;
        if (!pattern.test(this.value)) {
            this.setCustomValidity('Full name should only contain a to z, A to Z and spaces.');
        } else {
            this.setCustomValidity('');
        }
    });