(function(){
    var modal = document.getElementById("modalCenter");

    // Get the button that opens the modal
    var btn = document.getElementById("openModalCenter");

    // When the user clicks the button, open the modal
    btn.onclick = function() {
        modal.style.display = "block";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    $('.modal .btn-close').click(function(){
            modal.style.display = "none";
        });

        // Submit the form when the submit button is clicked
        $('#submitButton').click(function(){
            // Add your form submission logic here
            // For example, you can use AJAX to submit the form data to the server
            // After successful submission, you can close the modal
            modal.hide();
        });
}())