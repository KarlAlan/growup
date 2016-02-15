'use strict';

angular.module('growupApp')
    .factory('Task', function ($resource, DateUtils) {
        return $resource('api/tasks/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.declareDate = DateUtils.convertLocaleDateFromServer(data.declareDate);
                    data.auditDate = DateUtils.convertLocaleDateFromServer(data.auditDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.declareDate = DateUtils.convertLocaleDateToServer(data.declareDate);
                    data.auditDate = DateUtils.convertLocaleDateToServer(data.auditDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.declareDate = DateUtils.convertLocaleDateToServer(data.declareDate);
                    data.auditDate = DateUtils.convertLocaleDateToServer(data.auditDate);
                    return angular.toJson(data);
                }
            }
        });
    });
