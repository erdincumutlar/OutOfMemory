$(document).ready(function() {
	$("input.user").change(function() {
		var defect = $(this).prop('id').substring(0,$(this).prop('id').indexOf('_'));
		if ($('span#' + defect).is(":visible")) {
			$('span#' + defect).hide(300);		
		}				
	});    
});

function validate() {
	
	var validForm = true;	// Assume input is valid
	var megaByteRegex = /^(\s*|\d+)$/;	// Limit to < 10 GB (max: 9999 MB)		
	
	$('input.user').each(function(){		
		var defect = $(this).prop('id').substring(0,$(this).prop('id').indexOf('_'));	// Get defect name from input field			
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