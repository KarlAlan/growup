'use strict';

angular.module('growupApp')
    .factory('ConsumeSearch', function ($resource) {
        return $resource('api/_search/consumes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
