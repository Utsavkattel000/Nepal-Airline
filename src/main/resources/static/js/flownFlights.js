var filters = {
    name: "",
    date: ""
};

function filterFlights() {
    filters.name = document.getElementById("searchInput").value.toUpperCase();
    applyFilters();
}

function filterFlightsByDate() {
    filters.date = document.getElementById("searchDate").value.toUpperCase();
    applyFilters();
}

function applyFilters() {
    var table, tr, tdName, tdDate, i, txtValueName, txtValueDate;
    table = document.getElementsByClassName("table")[0];
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        tdName = tr[i].getElementsByTagName("td")[5]; // Column index for airline is 5
        tdDate = tr[i].getElementsByTagName("td")[0]; // Column index for date is 1
        if (tdName && tdDate) {
            txtValueName = tdName.textContent || tdName.innerText;
            txtValueDate = tdDate.textContent || tdDate.innerText;
            if (txtValueName.toUpperCase().indexOf(filters.name) > -1 && txtValueDate.toUpperCase().indexOf(filters.date) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}
