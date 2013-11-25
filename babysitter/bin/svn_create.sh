svnadmin create /home/hht/program/svnroot
svn import /home/hht/workspace/babysitter file:///home/hht/program/svnroot -m "init"

svnserve -d -r /home/hht/program/svnroot