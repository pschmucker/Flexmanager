dojo.require("dijit.form.DateTextBox");
dojo.declare("MyDateTextBox", dijit.form.DateTextBox, {
	constructor : function() {
		this.value = new Date();
	},
	serialize: function(d, options) {
		return dojo.date.locale.format(d, {selector:'date', datePattern:'dd/MM/yyyy HH:mm:ss'});
	}
});
dojo.provide("MyDateTextBox");

function initDatePicker(name, value){
	dojo.addOnLoad(function(){
		var textBox = new MyDateTextBox({
			name: name,
			value: value
		}, name);
	});
}
