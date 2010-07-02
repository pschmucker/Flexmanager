
function deleteObject(msg, object, action){
	if (confirm(msg + '\n\n' + object)) {
		window.location=action;
	}
}
