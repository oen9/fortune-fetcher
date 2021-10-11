#!/bin/bash
sudo cp ./target/native-image/fortune-fetcher /usr/local/bin/

sudo chgrp users /usr/local/bin/fortune-fetcher
sudo chmod g+x /usr/local/bin/fortune-fetcher
