$(document).ready(function() {
	$("input.user").change(function() {
		var defect = $(this).prop('id').substring(0,$(this).prop('id').indexOf('_'));
		if ($('span#' + defect).is(":visible")) {
			$('span#' + defect).hide(300);		
		}				
	});    
});

function validate() {

	$('input.user').each(function(){
	
		// Assume input is valid
		validForm = true;
		
		// Limit to < 10 GB (max: 9999 MB)
		var megaByteRegex = /^(\s*|\d+)$/;
		
		// Get defect name from input field
		var defect = $(this).prop('id').substring(0,$(this).prop('id').indexOf('_'));	
			
		if(!(megaByteRegex.test($.trim($(this).val())))) {
			$('span#' + defect).text("Numbers only.").show("fast");
			validForm = false;
		}	
		if ($.trim($(this).val()) == "") {
			$('span#' + defect).text("You can't leave anything empty.").show("fast");
			validForm = false;
		}
	
	});
    return validForm;
}