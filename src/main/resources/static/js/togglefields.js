function toggleFields() {
        const deliveryMode = document.getElementById("deliveryMode").value;
        const courierCompany = document.getElementById("courierCompany");
        const courierCompanyDiv = document.getElementById("courierCompanyDiv");
        const trackingIdDiv = document.getElementById("trackingIdDiv");
        const trackingId = document.getElementById("trackingId");

        if (deliveryMode === "self") {

		courierCompanyDiv.style.display="none";
		trackingIdDiv.style.display="none";
            courierCompany.disabled = true;
            trackingId.disabled = true;
            courierCompany.value = ""; // Clear values when disabled
            trackingId.value = ""; // Clear values when disabled
        } else {

		courierCompanyDiv.style.display="block";
		trackingIdDiv.style.display="block";
            courierCompany.disabled = false;
            trackingId.disabled = false;
        }
    }

    // Call the function on page load in case "self" is selected initially
    window.onload = toggleFields;