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

    document.addEventListener('DOMContentLoaded', function () {
        const profileImg = document.getElementById('profileImg');
        const popupDiv = document.getElementById('popupDiv');

        profileImg.addEventListener('click', function (event) {
            popupDiv.style.display = 'block';
            event.stopPropagation(); // Prevent click from propagating to document
        });

        document.addEventListener('click', function () {
            popupDiv.style.display = 'none';
        });

        popupDiv.addEventListener('click', function (event) {
            event.stopPropagation(); // Prevent click inside popup from closing it
        });
    });

searchInput.addEventListener("blur", function() {
	if (!options.includes(searchInput.value.toUpperCase())) {
		searchInput.value = '';

	}
});

searchInput2.addEventListener("blur", function() {
	if (!options.includes(searchInput2.value.toUpperCase())) {
		searchInput2.value = "";

	}
});

//take value from html
  var depDateInput = document.querySelector('input[name="depDate"]');

// Get today's date
var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
var yyyy = today.getFullYear();
var todayString = yyyy + '-' + mm + '-' + dd;

// Set the minimum attribute of the input field to today's date
depDateInput.setAttribute('min', todayString);

// Calculate the maximum date (today + 12 days)
var maxDate = new Date(today);
maxDate.setDate(maxDate.getDate() + 12);
var max_dd = String(maxDate.getDate()).padStart(2, '0');
var max_mm = String(maxDate.getMonth() + 1).padStart(2, '0'); // January is 0!
var max_yyyy = maxDate.getFullYear();
var maxDateString = max_yyyy + '-' + max_mm + '-' + max_dd;

// Set the maximum attribute of the input field to 12 days into the future
depDateInput.setAttribute('max', maxDateString);


function redirectToHistory() {
	window.location.href = "/history";
}
function redirectToPending() {
	window.location.href = "/pending";
}
function redirectToLogout() {
	window.location.href = "/logout";
}
