/*jslint browser: true*/
/*global $, jQuery*/
/*global window */
$(document).ready(function () {
  "use strict";
  $("input.inblock").change(function () {
      var defect = $(this).prop('id').substring(0, $(this).prop('id').indexOf('_'));
      if ($('span#' + defect).text() !== "") {
    	  $('span#' + defect).hide(300);  
      }
  });
    $("input.case").change(function () {
        $('span#caseError').hide(300);
    });

    $('input.case').each(function () {
        if ($.trim($(this).val()) === "") {
            $('input#caseSubmit').show();
        } else {
            $('input.case').attr("disabled", true);
            $('input#caseSubmit').hide();
            $('span#edit').show();
        }
    });

    // Set width of certain labels to the size of the largest label
    var max = 0,
        count = 0,
        sum = 0,
        avg = 0;

    $("label.inblock").each(function() {
        if ($(this).width() > max) {
            max = $(this).width();
        }
    });
    $("label.inblock").width(max);

    // Set width of certain labels to an averaged size
    $("label.question").each(function() {
        count += 1;
        sum += $(this).width();
    });
    if (count > 0) {
        avg = sum / count;
        $("label.question").width(avg);
    }
});

function validateCase() {
    "use strict";
    var validForm = true,
        numbersRegex = /^(\s*|\d+)$/,
        tagRegex = /([0-9]+)_([0-9]+)_([0-9]+)/;
    $('input.case').each(function () {
        if (($(this).prop('id') === 'caseNumber') && !(numbersRegex.test($.trim($(this).val())))) {
            $('span#caseError').text("Case numbers can't contain letters.").show("fast");
            validForm = false;
        }
        if (($(this).prop('id') === 'tag') && !(tagRegex.test($.trim($(this).val())))) {
            $('span#caseError').text("This doesn't look like a Mobilizer tag (e.g. MOBILIZER_7_6_2_0_20130118_1157-RELEASE)").show("fast");
            validForm = false;
        }
        if ($.trim($(this).val()) === "") {
            $('span#caseError').text("You can't leave anything empty.").show("fast");
            validForm = false;
        }
    });
    if (validForm) {
        $('input#caseSubmit').hide();
        $('span#edit').show();
    }
    return validForm;
}

function showCaseSubmit() {
    "use strict";
    $('input#caseSubmit').show();
    $('span#edit').hide();
    $('input.case').attr("disabled", false);
}

function validateDefects() {
    "use strict";
    var validForm = true,
        numbersRegex = /^(\s*|\d+)$/;
    $('input.inblock').each(function () {
        var defect = $(this).prop('id').substring(0, $(this).prop('id').indexOf('_'));
        if (!(numbersRegex.test($.trim($(this).val())))) {
            $('span#' + defect).text("Numbers only.").show("fast");
            validForm = false;
        }
        if ($.trim($(this).val()) === "") {
            $('span#' + defect).text("You can't leave anything empty.").show("fast");
            validForm = false;
        }
    });
    return validForm;
}

function info(url, windowname) {
    "use strict";
    window.open(url, windowname, "resizable=yes,toolbar=no,scrollbars=no,menubar=no,status=no,directories=no,width=350,height=400,left=600,top=300");
}