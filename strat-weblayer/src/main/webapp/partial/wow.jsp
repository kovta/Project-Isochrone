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

<script>
	$().button('toggle')
</script>

<script>
	$('#radioBtn a').on('click', function(){
	    var sel = $(this).data('title');
	    var tog = $(this).data('toggle');
	    $('#'+tog).prop('value', sel);
	    
	    $('a[data-toggle="'+tog+'"]').not('[data-title="'+sel+'"]').removeClass('active').addClass('notActive');
	    $('a[data-toggle="'+tog+'"][data-title="'+sel+'"]').removeClass('notActive').addClass('active');
	    $('input[name="confidentiality"]').val($(this).data('title'))
	})
</script>

<script>
	$("#compslider").slider({
	    ticks: [0, 25, 50, 75, 100],
	    ticks_labels: ['0%', '25%', '50%', '75%', '100%'],
	    ticks_snap_bounds: 7
	});
</script>

<script>
function goBack() {
    window.history.back();
}
</script>