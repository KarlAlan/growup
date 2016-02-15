'use strict';

angular.module('growupApp')
    .factory('Consume', function ($resource, DateUtils) {
        return $resource('api/consumes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.applyDate = DateUtils.convertLocaleDateFromServer(data.applyDate);
                    data.auditDate = DateUtils.convertLocaleDateFromServer(data.auditDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.applyDate = DateUtils.convertLocaleDateToServer(data.applyDate);
                    data.auditDate = DateUtils.convertLocaleDateToServer(data.auditDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.applyDate = DateUtils.convertLocaleDateToServer(data.applyDate);
                    data.auditDate = DateUtils.convertLocaleDateToServer(data.auditDate);
                    return angular.toJson(data);
                }
            }
        });
    });
