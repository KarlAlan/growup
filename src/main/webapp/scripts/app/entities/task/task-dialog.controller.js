'use strict';

angular.module('growupApp').controller('TaskDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Task', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Task, User) {

        $scope.task = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Task.get({id : id}, function(result) {
                $scope.task = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('growupApp:taskUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.task.id != null) {
                Task.update($scope.task, onSaveSuccess, onSaveError);
            } else {
                Task.save($scope.task, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDeclareDate = {};

        $scope.datePickerForDeclareDate.status = {
            opened: false
        };

        $scope.datePickerForDeclareDateOpen = function($event) {
            $scope.datePickerForDeclareDate.status.opened = true;
        };
        $scope.datePickerForAuditDate = {};

        $scope.datePickerForAuditDate.status = {
            opened: false
        };

        $scope.datePickerForAuditDateOpen = function($event) {
            $scope.datePickerForAuditDate.status.opened = true;
        };
}]);
