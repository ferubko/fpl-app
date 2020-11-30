    $(document).ready(function(){
        $('.eBtn').on('click', function(event){
            //prevents redirection to blank page with JSON-Task-Object
            event.preventDefault();
            var href = $(this).attr('href');
            $.get(href, function(user){
                $('#allowed').val(user.allowedToReceiveMessage);
            });
            $('#editUser').modal();
        });
    });