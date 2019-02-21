/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */

$(document).ready(function() {
    
    $("#utf").attr("action", purl);
    
    //validate form submittion
    $("#utf").validate({
        rules: {
            name: {
                required: true,
                minlength: 3,
                maxlength: 250
            },
            desc: {
                maxlength: 1000
            },
            presenter: {
                required: true,
                minlength: 3,
                maxlength: 100                
            },
            tags: {
                maxlength: 250
            },            
            url: {
                required: true,
                urlPattern: true,
                utubeNoDuplicate: true,
                maxlength: 300
            }
        },
        messages: {
            url: {
                urlPattern: "Invalid YouTube Embed URL"
            }
        }
    });
    
    //new rule to validate embed url pattern
    $.validator.addMethod("urlPattern", function(value, element){
        return this.optional(element) || /^(http:\/\/|https:\/\/)(www.youtube.com)\/embed\/(.*)/.test(value);
    }, "Invalid URL");
    
    //new rule to validate whether embed url is already available
    $.validator.addMethod("utubeNoDuplicate", function(value, element) {
        value = value.trim();
        var ajaxUrl = RESOURCE_HOST + "/resource/utube?url=" + value;
        var outJson;
        
        $.ajax({
            url: ajaxUrl,
            cache: true,
            async: false,
            success: function(json) {
                outJson = json;
            }
        });

        if($("#x").val().trim() === "" && outJson.id !== undefined && outJson.id !== "") {
            return false;
        }
        
        if($("#x").val().trim() !== "" && outJson.id !== undefined && $("#x").val().trim() !== outJson.id) {
            return false;
        }
        
        return true;
    }, "YouTube content already available");
});
