FROM usgswma/python:3.8

RUN apt-get install --no-install-recommends -y curl git
RUN curl -sL https://deb.nodesource.com/setup_13.x | bash - && apt-get install -y nodejs

RUN mkdir $HOME/.npm && chmod 777 $HOME/.npm/ && chmod 777 $HOME/

USER $USER
