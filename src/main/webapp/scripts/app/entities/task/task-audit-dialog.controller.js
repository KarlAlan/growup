'use strict';

angular.module('growupApp').controller('TaskAuditController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Task', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Task, User) {

            $scope.task = entity;
            $scope.users = User.query();
            $scope.load = function(id) {
                Task.get({id : id}, function(result) {
                    $scope.task = result;
                });
            };
            $scope.clear = function() {
                $uibModalInstance.dismiss('cancel');
            };
            $scope.audit = function () {
                Task.audit($scope.task,
                    function () {
                        $uibModalInstance.close(true);
                    });
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
