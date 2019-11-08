git pull
git add .
echo 'Enter commit message:'
read commitMessage
git commit -m "$commitMessage"
echo 'Enter branch name:'
read branchName
git push origin $branchName
echo 'Done!'
read