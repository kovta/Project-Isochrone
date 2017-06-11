<script>
	Template.editPrediction.onRendered(()=>{
		Materialize.updateTextFields()
   	})

    function init_map() {
        var var_location = new google.maps.LatLng(40.725118, -73.997699);

        var var_mapoptions = {
            center: var_location,
            zoom: 14
        };

        var var_map = new google.maps.Map(document.getElementById("map-container"),
            var_mapoptions);

        var_marker.setMap(var_map);
    }
    google.maps.event.addDomListener(window, 'load', init_map);
</script>

<script>
   	new WOW().init();
</script>

<script>
	$('#sandbox-container input').datepicker({
	    todayBtn: "linked",
	    clearBtn: true,
	    todayHighlight: true
	});
</script>