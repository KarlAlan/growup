'use strict';

angular.module('growupApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


