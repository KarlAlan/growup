'use strict';

angular.module('growupApp')
	.controller('ConsumeDeleteController', function($scope, $uibModalInstance, entity, Consume) {

        $scope.consume = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Consume.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
