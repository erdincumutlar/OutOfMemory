$(document).ready(function() {
	$("input.user").change(function() {
		var defect = $(this).prop('id').substring(0,$(this).prop('id').indexOf('_'));
		$('span#' + defect).hide(300);
	});    	
	$("input.case").change(function() {
		$('span#caseError').hide(300);
	});   
	$("select.case").change(function() {
		$('span#caseError').hide(300);
	});
});

function validateCase() {	
	var validForm = true;	
	var numbersRegex = /^(\s*|\d+)$/;
	var tagRegex = /([0-9]+)_([0-9]+)_([0-9]+)/;
	
	$('input.case').each(function(){	
		if(($(this).prop('id') == 'caseNumber') && !(numbersRegex.test($.trim($(this).val())))) {
			$('span#caseError').text("Case numbers can't contain letters.").show("fast");
			validForm = false;
		}
		if(($(this).prop('id') == 'tag') && !(tagRegex.test($.trim($(this).val())))) {
			$('span#caseError').text("This doesn't look like a Mobilizer tag (e.g. MOBILIZER_7_6_2_0_20130118_1157-RELEASE)").show("fast");
			validForm = false;
		}
		if ($.trim($(this).val()) == "") {
			$('span#caseError').text("You can't leave anything empty.").show("fast");
			validForm = false;
		}	
	});	
	$('select.case').each(function(){
		if(!($('span#caseError').is(":visible")) ){ 
			if ($.trim($(this).val()) == "") {
				$('span#caseError').text("Case owner is required").show("fast");
				validForm = false;
			}
		} 
	});			
    return validForm;
}

function validateDefects() {	
	var validForm = true;
	var numbersRegex = /^(\s*|\d+)$/;	
	
	$('input.user').each(function(){		
		var defect = $(this).prop('id').substring(0,$(this).prop('id').indexOf('_'));		
		if(!(numbersRegex.test($.trim($(this).val())))) {
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