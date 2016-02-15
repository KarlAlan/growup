'use strict';

angular.module('growupApp')
    .controller('ConsumeDetailController', function ($scope, $rootScope, $stateParams, entity, Consume, User) {
        $scope.consume = entity;
        $scope.load = function (id) {
            Consume.get({id: id}, function(result) {
                $scope.consume = result;
            });
        };
        var unsubscribe = $rootScope.$on('growupApp:consumeUpdate', function(event, result) {
            $scope.consume = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
