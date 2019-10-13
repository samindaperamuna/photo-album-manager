/**
 * Fetches the thumbnails from the file system and loads them into HTML.
 */
function fetchImages() {
	$.get("/thumbnails").done(function(data) {
		$.each(data, function (index, value) {
			$('#gallery').append("<div class='col-lg-3 col-md-4 col-xs-6'>"
					+ "<a href='/image?url=" + encodeURIComponent(value) + "' class='d-block mb-4 h-100'>"
					+ "<img class='img-fluid img-thumbnail' src='" + value + "' alt='" + value + "'>"
					+ "</a>"
				    + "</div>"
			);
		});
	});
}

$('document').ready(function() {
	fetchImages();
});