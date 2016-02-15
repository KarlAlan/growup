'use strict';

angular.module('growupApp').controller('ConsumeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Consume', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Consume, User) {

        $scope.consume = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Consume.get({id : id}, function(result) {
                $scope.consume = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('growupApp:consumeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.consume.id != null) {
                Consume.update($scope.consume, onSaveSuccess, onSaveError);
            } else {
                Consume.save($scope.consume, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForApplyDate = {};

        $scope.datePickerForApplyDate.status = {
            opened: false
        };

        $scope.datePickerForApplyDateOpen = function($event) {
            $scope.datePickerForApplyDate.status.opened = true;
        };
        $scope.datePickerForAuditDate = {};

        $scope.datePickerForAuditDate.status = {
            opened: false
        };

        $scope.datePickerForAuditDateOpen = function($event) {
            $scope.datePickerForAuditDate.status.opened = true;
        };
}]);
