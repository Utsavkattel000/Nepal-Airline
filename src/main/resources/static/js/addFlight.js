const options = ["Bajura", "Bhadrapur", "Bhairahawa", "Bharatpur", "Bhojpur", "Biratnagar", "Dang",
    "Dhangadhi", "Dolpa", "Ilam", "Janakpur", "Jomsom", "Jumla", "Kathmandu", "Khanidanda",
    "Lukla", "Nepalgunj", "Phaplu", "Pokhara", "Rajbiraj", "Ramechhap", "Rara", "Rukum", "Simara",
    "Simikot", "Surkhet", "Talcha", "Taplejung", "Thamkharka", "Tumlingtar", "Varanasi"];


const searchInput = document.getElementById("searchInput");
const dropdownList = document.getElementById("dropdownList");
const searchInput2 = document.getElementById("searchInput2");
const dropdownList2 = document.getElementById("dropdownList2");

displayOptions(options);
displayOptions2(options);
// Event listener for clicking the dropdown
searchInput.addEventListener("click", function() {
	displayOptions(options); // Display unfiltered options
	dropdownList.style.display = "block"; // Show the list
});

searchInput2.addEventListener("click", function() {
	displayOptions2(options); // Display unfiltered options
	dropdownList2.style.display = "block"; // Show the list
});

// Event listener for typing in the input fields
searchInput.addEventListener("input", function() {
	const searchTerm = searchInput.value.toLowerCase();
	const filteredOptions = options.filter(option =>
		option.toLowerCase().includes(searchTerm)
	);
	displayOptions(filteredOptions); // Display filtered options
});

searchInput2.addEventListener("input", function() {
	const searchTerm2 = searchInput2.value.toLowerCase();
	const filteredOptions2 = options.filter(option =>
		option.toLowerCase().includes(searchTerm2)
	);
	displayOptions2(filteredOptions2); // Display filtered options
});

// Event listener for clicking outside the dropdown
window.addEventListener("click", function(event) {
	if (!searchInput.contains(event.target)) {
		dropdownList.style.display = "none"; // Hide the list
	}
});
window.addEventListener("click", function(event) {
	if (!searchInput2.contains(event.target)) {
		dropdownList2.style.display = "none"; // Hide the list
	}
});

// Display options in the dropdown list
function displayOptions(options) {
	dropdownList.innerHTML = "";
	options.forEach(option => {
		const item = document.createElement("div");
		item.classList.add("dropdown-item");
		item.textContent = option;
		item.addEventListener("click", function() {
			searchInput.value = option;
			dropdownList.style.display = "none"; // Hide the list after selection
		});
		dropdownList.appendChild(item);
	});
}
function displayOptions2(options) {
	dropdownList2.innerHTML = "";
	options.forEach(option => {
		const item2 = document.createElement("div");
		item2.classList.add("dropdown-item");
		item2.textContent = option;
		item2.addEventListener("click", function() {
			searchInput2.value = option;
			dropdownList2.style.display = "none"; // Hide the list after selection
		});
		dropdownList2.appendChild(item2);
	});
}


searchInput.addEventListener("blur", function() {
	if (!options.includes(searchInput.value.toUpperCase())) {
		searchInput.value = '';

	}
});
  var depDateInput = document.querySelector('input[name="depDate"]');


  document.getElementById("price").addEventListener("input", function() {
    var price = parseInt(this.value);
    if (price < 2000) {
      this.setCustomValidity("Price cannot be less than 2000. If it is contact a developer.");
    } else if (price > 25000) {
      this.setCustomValidity("Price should not be more than 25000. If it is contact a developer.");
    } else {
      this.setCustomValidity("");
    }
  });
     var aname = document.getElementById('name');
aname.addEventListener('input', function () {
    var pattern = /^[a-zA-Z0-9\- ]*$/;
    if (!pattern.test(this.value)) {
        this.setCustomValidity('Name should only contain letters (a to z, A to Z), numbers (0-9), spaces, and "-" sign.');
    } else {
        this.setCustomValidity('');
    }
});


  document.getElementById("capacity").addEventListener("input", function() {
    var capacity = parseInt(this.value);
    if (capacity < 15) {
      this.setCustomValidity("Capacity must be at least 15.");
    } else if (capacity > 100) {
      this.setCustomValidity("Capacity cannot exceed 100.");
    } else {
      this.setCustomValidity("");
    }
  });
document.getElementById('depTime').addEventListener('change', validateTime);
        document.getElementById('arrivalTime').addEventListener('change', validateTime);

        function validateTime() {
            const depTimeInput = document.getElementById('depTime');
            const arrivalTimeInput = document.getElementById('arrivalTime');
            const depTimeValue = depTimeInput.value;
            const arrivalTimeValue = arrivalTimeInput.value;

            if (depTimeValue && arrivalTimeValue) {
                const depTime = new Date(`1970-01-01T${depTimeValue}:00`);
                const arrivalTime = new Date(`1970-01-01T${arrivalTimeValue}:00`);
                const minArrivalTime = new Date(depTime.getTime() + 25 * 60000);

                if (arrivalTime < minArrivalTime) {
                    arrivalTimeInput.setCustomValidity('Arrival time must be at least 25 minutes later than departure time.');
                } else {
                    arrivalTimeInput.setCustomValidity('');
                }
            } else {
                arrivalTimeInput.setCustomValidity('');
            }
        }
function redirectToSignup() {
	window.location.href = "/signup";
}
function redirectToLogin() {
	window.location.href = "/login";
}



