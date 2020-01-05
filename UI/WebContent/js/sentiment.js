/**
 * 
 */
$('#frmSearch').submit(function(e) {
	e.preventDefault();
	
	var keyword = $("#frmSearch input[name=keyword]").val();

	$.get("/WordSentiment/Search", { keyword : keyword })
		.done(function(result) {
			var data = $.parseJSON(result);

			deleteMarkers();
			for(var i = 0; i < data.length; i++)
			{
				var item = data[i];
				addMarker(item.Latitude, item.Longtitude, item.Sentiment, item.Text);
			}
		});
});

function addMarker(lat, lng, polarity, text)
{
	if (polarity != 2)
	{
		var marker = new google.maps.Marker({
			position: { lat: lat, lng: lng },
			map: map,
			icon: polarity > 2 ? 'img/like.png' : 'img/dislike.png',
			title: text
		});
		markers.push(marker);
	}
}

function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(map);
    }
  }

function clearMarkers() {
    setMapOnAll(null);
  }

function showMarkers() {
    setMapOnAll(map);
  }

function deleteMarkers() {
    clearMarkers();
    markers = [];
  }