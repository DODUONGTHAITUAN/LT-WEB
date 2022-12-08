function handleGetBaseUrl() {
	const { hostname, host, protocol } = window.location;
	let url = "";
	if (hostname !== 'localhost') {
		url = `${protocol}//${host}`;
	} else {
		url = `${protocol}//${host}/shoplane-ft`;
	}
	return url;
}

function handleSelectStatus(elm) {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);

	const pageSize = urlParams.get('page_size');

	window.location = '?status=' + elm.value + '&current_page=1&page_size=' + pageSize;

}
function handleSelectEntries(elm) {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);

	const status = urlParams.get('status');

	window.location = '?status=' + status + '&current_page=1&page_size=' + elm.value;
}