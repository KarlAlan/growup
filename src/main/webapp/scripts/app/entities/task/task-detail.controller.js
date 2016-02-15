'use strict';

angular.module('growupApp')
    .controller('TaskDetailController', function ($scope, $rootScope, $stateParams, entity, Task, User) {
        $scope.task = entity;
        $scope.load = function (id) {
            Task.get({id: id}, function(result) {
                $scope.task = result;
            });
        };
        var unsubscribe = $rootScope.$on('growupApp:taskUpdate', function(event, result) {
            $scope.task = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
