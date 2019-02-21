/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */

var RESOURCE_HOST = "https://res.study.com:8082";
var AUTH_HOST = "https://auth.study.com:8083";

//new rule to validate embed url pattern
$.validator.addMethod("emailformat", function(value, element){
    return this.optional(element) || /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/.test(value);
}, "Invalid email address");

//new rule to validate whether email is already available
$.validator.addMethod("uniqueemail", function(value, element) {
    value = value.trim();
    if(value === "") {
        return true;
    }
    
    var ajaxUrl = AUTH_HOST + "/auth?email=" + value;
    var outJson;

    $.ajax({
        url: ajaxUrl,
        cache: true,
        async: false,
        success: function(json) {
            outJson = json;
        }
    });

    if(outJson !== null && outJson.id !== "") {
        return false;
    }
    
    return true;
}, "Email already in use");