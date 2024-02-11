@echo off

cd %~dp0

SET MEASUREMENT_STACK_NAME=measurement-stack
SET MEASUREMENT_SQS=measurement-stack


FOR /F "tokens=* USEBACKQ" %%F IN (`aws cloudformation describe-stacks --stack-name %MEASUREMENT_STACK_NAME% --query "Stacks[0].Outputs[?OutputKey=='MeasurementQueueArn'].OutputValue" --output text`) DO (
    SET SQS_QUEUE_ARN=%%F
)
echo The ARN of the measurement queue is %SQS_QUEUE_ARN%...
