
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
      // You can also provide a message to the user to indicate the valid range
      // alert('Please enter an age between 1 and 120.');
    }
  });





































// // Function to submit the forms
// function submitForms() {
//     // Create a form element
//     var form = document.createElement('form');
//     form.setAttribute('method', 'post');
//     form.setAttribute('action', '/book'); // Set the URL to submit the form data

//     // Get the number of forms
//     var numberOfForms = document.getElementById('numberOfForms').value;

//     // Loop through each form and collect data
//     for (var i = 1; i <= numberOfForms; i++) {
//         var formItem = document.getElementById('form' + i);

//         // Clone form items to append to the main form
//         var clonedFormItem = formItem.cloneNode(true);

//         // Append cloned form item to the main form
//         form.appendChild(clonedFormItem);
//     }

//     // Append the form to the document body (so it becomes part of the DOM)
//     document.body.appendChild(form);

//     // Submit the form
//     form.submit();

//     // Remove the form from the DOM (optional)
//     document.body.removeChild(form);
// }

// // Function to generate forms based on the selected number
// function generateForms() {
//     var container = document.getElementById('container');
//     container.innerHTML = ''; // Clear previous forms

//     var numberOfForms = document.getElementById('numberOfForms').value;

//     for (var i = 1; i <= numberOfForms; i++) {
//         var form = document.createElement('div');
//         form.className = 'form';
//         form.id = 'form' + i;
//         form.innerHTML = `
//       <h3>Passenger ${i}</h3>
//       <label for="name${i}">Full Name:</label>
//       <input type="text" class="input" name="fullName${i}" required>
//       <label for="phone${i}">Phone:</label>
//       <input type="text" class="input" name="phone${i}" required>
//       <label for="email${i}">Email:</label>
//       <input type="email" class="input" name="email${i}" required>
//       <label for="age${i}">Age:</label>
//       <input type="number" class="input" name="age${i}" required>
//       <label for="gender${i}">Gender:</label>
//       <select id="gender${i}" name="gender${i}" class="gender">
//         <option value="male">Male</option>
//         <option value="female">Female</option>
//       </select>
//       <label for="nationality${i}">Nationality:</label>
//       <input type="text" class="input" name="nationality${i}" required> <br>`;
//         container.appendChild(form);
//     }
// }

// // Generate initial form
// generateForms();
