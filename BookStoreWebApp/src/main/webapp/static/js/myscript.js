$("#inputSearch" ).autocomplete({
	source: function( request, response ) {
		const keyword = $('#inputSearch').val();
		const url = `http://localhost:8080/ajax-search-book?keyword=${keyword}`;
		$.getJSON(url, function(data) {
			console.log(data);
			response( data );
		})
	},
	minLength: 2,
	select: function( event, ui ) {
	  log( "Selected: " + ui.item.value + " aka " + ui.item.id );
	}
  }).autocomplete( "instance" )._renderItem = function( ul, item ) {
	return $( "<li>" )
	  .append(`<a style='display:block' href='/book.html?id=${item.id}'>${item.name}</a>`)
	  .appendTo( ul );
  };;
