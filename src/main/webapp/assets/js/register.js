/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
//https://www.youtube.com/watch?v=StUvRg_kT0c
$(document).ready(function() {
    
    $("#regForm").attr("action", purl);
    
    $("#regForm").validate({
        rules: {
            username: {
                required: true,
                minlength: 5,
                maxlength: 50
            },
            firstName: {
                required: true,
                minlength: 3,
                maxlength: 250
            },
            lastName: {
                required: true,
                minlength: 3,
                maxlength: 250
            },
            email: {
                required: true,
                emailformat: true,
                uniqueemail: true
            },
            passwd: {
                required: true,
                minlength: 8,
                maxlength: 25
            },
            repasswd: {
                required: true,
                minlength: 8,
                maxlength: 25,
                confirmpass: true
            }
        },
        mesages: {
            email: {
                uniqueemail: "Email already in use, please choose another"
            }
        }
    });
    
    //check for confirm password
    $.validator.addMethod("confirmpass", function(value, element) {
        if(value === "") {
            return true;
        }
        
        if(value === $("#passwd").val()) {
            return true;
        }
        
        return false;
    }, "Re-ented password is incorrect");    
});