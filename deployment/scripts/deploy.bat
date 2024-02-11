@echo off

cd %~dp0
cd ../..

echo Initializing params...

SET STACK_NAME=alert-stack
SET PARAMS=--resolve-s3 --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND
SET TEMPLATE_PATH=deployment\template\template.yaml

echo The stack name to be created/updated is %STACK_NAME%...

echo Creating JAR file to upload...
call mvnw package

IF %ERRORLEVEL% NEQ 0 (
    echo The artifact creation has failed.
    exit /b %ERRORLEVEL%
)
echo JAR File Created...

echo Ruta actual: %CD%

SET MEASUREMENT_STACK_NAME=measurement-stack
SET MEASUREMENT_SQS=measurement-stack


FOR /F "tokens=* USEBACKQ" %%F IN (`aws cloudformation describe-stacks --stack-name %MEASUREMENT_STACK_NAME% --query "Stacks[0].Outputs[?OutputKey=='MeasurementQueueArn'].OutputValue" --output text`) DO (
    SET SQS_QUEUE_ARN=%%F
)
echo The ARN of the measurement queue is %SQS_QUEUE_ARN%...


SET FINAL_PARAMS=--template-file %TEMPLATE_PATH% --stack-name %STACK_NAME% %PARAMS% --parameter-overrides MeasurementQueueArn=%SQS_QUEUE_ARN%

echo The creation/update of the Stack %STACK_NAME% will start with the next params [%FINAL_PARAMS%]
sam deploy %FINAL_PARAMS%

IF %ERRORLEVEL% NEQ 0 (
    echo The stack deployment has failed.
    exit /b %ERRORLEVEL%
)

echo The creation/update of the Stack %STACK_NAME% has finished successfully
